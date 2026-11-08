package tech.kayys.kallam.ijtihad.filesystem;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import tech.kayys.kallam.ijtihad.EvidenceTier;
import tech.kayys.kallam.ijtihad.RuleCard;
import tech.kayys.kallam.ijtihad.RuleCardRepository;
import tech.kayys.kallam.ijtihad.RuleCategory;

/**
 * Loads rule cards from Markdown files with a small flat YAML-style
 * frontmatter block - the same shape as this environment's own SKILL.md
 * files. Deliberately hand-rolled rather than pulling in a YAML library:
 * the frontmatter here is flat key:value pairs, and kallam-core's own
 * "stay dependency-light" discipline is worth keeping in a sibling module.
 * Swap this for a Andalus {@code SkillDefinitionStore}-backed implementation
 * once rule cards need versioning/lifecycle management beyond files on disk.
 *
 * <p>Frontmatter shape:
 * <pre>{@code
 * ---
 * id: tarjih.quran-quran-conflict
 * tier: QURAN
 * category: TARJIH
 * title: Tarjih between apparently conflicting ayat
 * appliesWhen: Two or more ayat appear to address the same mas'alah with apparently conflicting rulings
 * priority: 10
 * madhhab: common
 * ---
 * # Markdown body...
 * }</pre>
 */
public final class FileSystemRuleCardRepository implements RuleCardRepository {

    private final List<RuleCard> cards;

    public FileSystemRuleCardRepository(Path rulesRoot) {
        this.cards = loadAll(rulesRoot);
    }

    /** Convenience factory for cards packaged as classpath resources (the common case). */
    public static FileSystemRuleCardRepository fromClasspath(String resourceRoot) {
        try {
            URI uri = FileSystemRuleCardRepository.class.getClassLoader().getResource(resourceRoot).toURI();
            Path path;
            if ("jar".equals(uri.getScheme())) {
                FileSystem fs = FileSystems.newFileSystem(uri, Map.of());
                path = fs.getPath(resourceRoot);
            } else {
                path = Path.of(uri);
            }
            return new FileSystemRuleCardRepository(path);
        } catch (URISyntaxException | IOException | NullPointerException e) {
            throw new IllegalStateException("Could not load rule cards from classpath:" + resourceRoot, e);
        }
    }

    @Override
    public List<RuleCard> findApplicable(EvidenceTier tier, RuleCategory category) {
        return findApplicable(tier, category, null);
    }

    @Override
    public List<RuleCard> findApplicable(EvidenceTier tier, RuleCategory category, String madhhab) {
        return cards.stream()
                .filter(c -> c.category() == category)
                .filter(c -> tier == null || c.tier() == null || c.tier() == tier)
                .filter(c -> c.madhhab().equalsIgnoreCase("common")
                        || (madhhab != null && c.madhhab().equalsIgnoreCase(madhhab)))
                .sorted((a, b) -> Integer.compare(b.priority(), a.priority()))
                .toList();
    }

    @Override
    public List<RuleCard> findByCategory(RuleCategory category) {
        return cards.stream().filter(c -> c.category() == category).toList();
    }

    @Override
    public Optional<RuleCard> findById(String id) {
        return cards.stream().filter(c -> c.id().equals(id)).findFirst();
    }

    private List<RuleCard> loadAll(Path root) {
        try (var walk = Files.walk(root)) {
            List<RuleCard> result = new ArrayList<>();
            for (Path path : (Iterable<Path>) walk.filter(p -> p.toString().endsWith(".md"))::iterator) {
                result.add(parse(Files.readString(path)));
            }
            return List.copyOf(result);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load rule cards from " + root, e);
        }
    }

    private RuleCard parse(String content) {
        if (!content.startsWith("---")) {
            throw new IllegalArgumentException("Rule card is missing its --- frontmatter block");
        }
        int end = content.indexOf("\n---", 3);
        if (end < 0) {
            throw new IllegalArgumentException("Rule card frontmatter is not closed with ---");
        }
        String frontmatter = content.substring(3, end).strip();
        String body = content.substring(end + 4).strip();

        Map<String, String> fields = new java.util.LinkedHashMap<>();
        for (String line : frontmatter.split("\n")) {
            int colon = line.indexOf(':');
            if (colon < 0 || line.strip().isEmpty()) continue;
            fields.put(line.substring(0, colon).strip(), line.substring(colon + 1).strip());
        }

        String tierField = fields.get("tier");
        return new RuleCard(
                required(fields, "id"),
                tierField == null || tierField.equalsIgnoreCase("common") ? null : EvidenceTier.valueOf(tierField),
                RuleCategory.valueOf(required(fields, "category")),
                required(fields, "title"),
                fields.getOrDefault("appliesWhen", ""),
                Integer.parseInt(fields.getOrDefault("priority", "0")),
                fields.getOrDefault("madhhab", "common"),
                body);
    }

    private String required(Map<String, String> fields, String key) {
        String value = fields.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Rule card frontmatter is missing required field: " + key);
        }
        return value;
    }
}
