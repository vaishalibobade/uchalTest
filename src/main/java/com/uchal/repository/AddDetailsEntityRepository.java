package com.uchal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uchal.entity.AddDetailsEntity;

@Repository
public interface AddDetailsEntityRepository extends JpaRepository<AddDetailsEntity, Integer> {

	@Query("SELECT a FROM AddDetailsEntity a WHERE  a.startDate + a.durationDays >=  GETDATE()")
	public List<AddDetailsEntity> getAllAvailableAdds();	
}
