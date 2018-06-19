/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	//32.1 & 33.1
	@Query("select n.notes from Auditor n where n.id = ?1")
	Collection<Note> findNotesByAuditor(Integer auditorID);

	@Query("select t.notes from Trip t where t.id = ?1")
	Collection<Note> findNotesByTrip(Integer tripID);

	//35.4
	@Query("select min(t.notes.size), max(t.notes.size), avg(t.notes.size), sqrt((sum(t.notes.size * t.notes.size) / count(t.notes.size)) - avg(t.notes.size) * avg(t.notes.size)) from Trip t")
	Object findMinMaxAverStandardNotesPerTrip();
}
