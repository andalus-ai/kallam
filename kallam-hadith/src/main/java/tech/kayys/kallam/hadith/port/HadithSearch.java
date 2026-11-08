package tech.kayys.kallam.hadith.port;

import java.util.List;

/** Search port. Search projections must never overwrite source-attested text. */
public interface HadithSearch {
    List<HadithSearchHit> search(HadithSearchQuery query);
}
