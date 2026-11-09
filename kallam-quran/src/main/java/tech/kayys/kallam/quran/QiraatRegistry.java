package tech.kayys.kallam.quran;
import java.util.Optional;
/** Read port for explicitly curated Qira'at metadata. */
public interface QiraatRegistry {
    Optional<QiraatDefinition> find(QiraatId id);
}
