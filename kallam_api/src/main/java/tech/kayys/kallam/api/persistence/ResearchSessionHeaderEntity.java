package tech.kayys.kallam.api.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * The one piece of session data that isn't a fold over events: the
 * question itself and when the session was opened. Written once, at
 * session creation, and never revised in place - if the question's framing
 * ever needs to change, that should be a new session and a link between
 * them, not a silent edit here.
 */
@Entity
@Table(name = "research_session_header")
public class ResearchSessionHeaderEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", length = 64)
    public String id;

    @Column(name = "masalah_question", nullable = false, length = 2000)
    public String masalahQuestion;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;
}
