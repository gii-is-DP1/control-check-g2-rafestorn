package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.pet.PetType;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedingService {
	
	@Autowired
	private FeedingRepository fr;
	
    public List<Feeding> getAll(){
        return fr.findAll();
    }

    public List<FeedingType> getAllFeedingTypes(){
        return fr.findAllFeedingTypes();
    }

    public FeedingType getFeedingType(String typeName) {
        return fr.findFeedingTypeByName(typeName);
    }

    @Transactional(rollbackFor = UnfeasibleFeedingException.class)
    public Feeding save(Feeding p) throws DataAccessException, UnfeasibleFeedingException {
    	PetType pt1 = p.getPet().getType();
    	PetType pt2 = p.getFeedingType().getPetType();
    	if(pt1!=pt2) {
    		throw new UnfeasibleFeedingException();
    	}else {
    		fr.save(p);
    	}
        return p;       
    }

    
}
