package org.springframework.samples.petclinic.feeding;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FeedingRepository extends CrudRepository<Feeding, Integer>{
    List<Feeding> findAll();
    
    @Query("SELECT fType FROM FeedingType fType")
    List<FeedingType> findAllFeedingTypes();
    
    Optional<Feeding> findById(int id);
    Feeding save(Feeding p);
    
    @Query("SELECT fType FROM FeedingType fType where fType.name=?1")
    FeedingType findFeedingTypeByName(String name);
    
    
}
