package tech.kayys.kallam.ijtihad;

import java.util.List;
import tech.kayys.kallam.istinbat.Evidence;
import tech.kayys.kallam.istinbat.Masalah;

/**
 * What an expert needs to do its analysis: the question as framed
 * (kallam-core's {@code Masalah}) and whatever evidence retrieval has
 * surfaced so far ({@code Evidence}, also kallam-core) - reused directly
 * rather than duplicated, since experts analyze the same evidence the
 * research pipeline already retrieved.
 *
 * <p>{@code madhhab} selects which school's methodology cards apply on
 * top of the common ones (see {@link RuleCardRepository#findApplicable}) -
 * null means "compare across schools" rather than commit to one, which is
 * itself a valid mode (feeds {@code IkhtilafBlock} on the Flutter/API
 * side rather than a single determination).
 */
public record MasalahContext(String sessionId, Masalah masalah, List<Evidence> evidence, String madhhab) {
    public MasalahContext {
        evidence = evidence == null ? List.of() : List.copyOf(evidence);
    }

    public MasalahContext(String sessionId, Masalah masalah, List<Evidence> evidence) {
        this(sessionId, masalah, evidence, null);
    }
}
