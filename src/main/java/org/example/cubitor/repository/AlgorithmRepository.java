package org.example.cubitor.repository;

import org.example.cubitor.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository <Algorithm, Long>{
    Algorithm findByName(String name);


}
