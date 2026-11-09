package tech.kayys.kallam.quran;
import java.util.Optional;
/** Read port for tariq metadata; adapters must validate its parent riwayah relationship. */
public interface TariqRegistry {
    Optional<TariqDefinition> find(TariqId id);
}
