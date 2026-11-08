package tech.kayys.kallam.hadith.application;

import tech.kayys.kallam.hadith.domain.HadithCollection;
import tech.kayys.kallam.hadith.domain.HadithEdition;
import tech.kayys.kallam.hadith.domain.HadithOccurrence;
import tech.kayys.kallam.hadith.port.HadithSearchHit;

/** Resolved source occurrence returned from discovery, with retrieval metadata kept separate. */
public record HadithDiscoveryResult(
        HadithOccurrence occurrence,
        HadithCollection collection,
        HadithEdition edition,
        HadithSearchHit match) {}
