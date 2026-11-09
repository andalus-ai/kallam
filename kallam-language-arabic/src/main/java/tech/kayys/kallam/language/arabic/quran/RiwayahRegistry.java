package tech.kayys.kallam.language.arabic.quran;
import java.util.Optional;
/** Read port for riwayah metadata; adapters must validate its parent Qira'at relationship. */
public interface RiwayahRegistry {
    Optional<RiwayahDefinition> find(RiwayahId id);
}
