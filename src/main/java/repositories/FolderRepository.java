/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;
import domain.FolderType;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	//find the sender´s folder.
	@Query("select f from Actor a join a.folders f where a.id = ?1 and f.name like %?2%")
	Collection<Folder> findFoldersOfActor(int id, String name);

	@Query("select f from Folder f where ?1 MEMBER OF f.messages")
	Folder findByMessage(int messageId);

	@Query("select a.folders from Actor a  where a.id = ?1")
	Collection<Folder> findFoldersByActor(int id);

	@Query("select f from Actor a join a.folders f where a.id= ?1 and f.folderType = ?2")
	Collection<Folder> findCustomFolderByActor(int id, FolderType folderType);

	@Query("select f from Folder f where ?1 MEMBER OF f.childs")
	Folder findFolderParent(int folderId);

}
