package com.luu.telemed.services;

import java.util.Optional;

import com.luu.telemed.models.ERole;
import com.luu.telemed.models.Role;


public interface RoleService {
  /**
   * Trouver le Role par son nom
   * 
   * @param name
   * @return List<Doctor>
   */
  Optional<Role> findByName(ERole name);

  /**
   * supprimer le Role par son nom
   * 
   * @param name
   * @return List<Doctor>
   */
  void deleteByName(ERole name);

  /**
   * supprimer tout les Roles
   * 
   * @param name
   * @return List<Doctor>
   */
  void deleteAll();

  /**
   * Ajouter un Role
   * 
   * @param name
   * @return List<Doctor>
   */
  Optional<Role> save(ERole name);


}
