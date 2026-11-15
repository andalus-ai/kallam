package tech.kayys.kallam.hadith.application.isnad;
import tech.kayys.kallam.hadith.domain.isnad.NarratorId;
public record SharedNarrator(NarratorId narratorId, int firstChainPosition, int secondChainPosition) {}