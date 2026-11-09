package tech.kayys.kallam.quran;
import java.util.Objects;
/** Explicit Qira'at → riwayah → optional tariq path. */
public record ReadingPath(QiraatId qiraatId, RiwayahId riwayahId, TariqId tariqId) {
    public ReadingPath {
        Objects.requireNonNull(qiraatId, "qiraatId");
        Objects.requireNonNull(riwayahId, "riwayahId");
    }
}
