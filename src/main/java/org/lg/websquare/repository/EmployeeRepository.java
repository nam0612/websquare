package org.lg.websquare.repository;

import org.lg.websquare.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


    @Query(value = """
        select * from employee e
        where 1=1
        and (e.name like lower(:name) or :name is null)
        and (e.team like lower(:team) or :team is null)
        and (e.phone like lower(:phone) or :phone is null)
        and (e.gender = lower(:gender) or :gender is null)
        and (e.birth_date >= :fromDate or :fromDate is null)
        and (e.birth_date <= :toDate or :toDate is null)
    """, nativeQuery = true)
    Page<Employee> search(
            @Param("name") String name,
            @Param("team") String team,
            @Param("phone") String phone,
            @Param("gender") String gender,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            Pageable pageable
    );

    @Query(value = """
        select * from employee e
        where 1=1
        and (e.name like lower(:name) or :name is null)
        and (e.team like lower(:team) or :team is null)
        and (e.phone like lower(:phone) or :phone is null)
        and (e.gender = lower(:gender) or :gender is null)
        and (e.birth_date >= :fromDate or :fromDate is null)
        and (e.birth_date <= :toDate or :toDate is null)
    """, nativeQuery = true)
    List<Employee> downloadsExcel(
            @Param("name") String name,
            @Param("team") String team,
            @Param("phone") String phone,
            @Param("gender") String gender,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate
    );
}
