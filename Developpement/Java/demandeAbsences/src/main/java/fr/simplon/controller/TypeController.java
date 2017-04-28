package fr.simplon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.simplon.services.TypeService;
import fr.simplon.domain.Type;

@RestController
@RequestMapping("/type")
public class TypeController {

		@Autowired
		TypeService typeService;

		/**
		 * Liste des types
		 * @param searchnew : 2eme critere de recherche 
		 * @return liste des acteurs
		 */
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<?> listTypes(@RequestParam(value="searchnew", defaultValue="") String searchnew) {
			Iterable<Type> listType;
			try {
				listType = typeService.listTypes(searchnew);
			} catch (Exception e) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_EXEC, e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK).body(listType);
		}

		/**
		 * recherche d'un acteur. note : l'id est dans l'url et non en parametre
		 * 
		 * @param id : id de l'acteur
		 * @return : objet acteur
		 */
		@RequestMapping(value = "/{id}", method = RequestMethod.GET)
		public ResponseEntity<?> getType(@PathVariable("id") Long id) {
			Type type = null;
			try {
				type = typeService.getType(id);
			} catch (Exception e) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_EXEC, e.getMessage());
			}
			if (type == null) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_NOT_FOUND, "Not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(type);
		}

		/**
		 * creation d'un acteur
		 * 
		 * @param type : acteur
		 * @param errors : erreur de validation
		 * @return : réponse de la requête
		 */
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<?> insertType(@Valid @RequestBody Type type , Errors errors) {

			// Si erreur de validation, retour erreur 400 (bad request), avec le
			// message d'erreur
			if (errors.hasErrors()) {
				return AjaxResponseError.extractErrorWhenIncompletRequest(errors);
			}

			try {
				type = typeService.insertType(type);
			} catch (Exception e) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_EXEC, e.getMessage());
			}

			return ResponseEntity.status(HttpStatus.OK).body(type);
		}
		
		/**
		 * 
		 * @param type : acteur
		 * @param errors : erreurs de validation
		 * @return : réponse de la requête
		 */
		@RequestMapping(method = RequestMethod.PUT)
		public ResponseEntity<?> updateType(@Valid @RequestBody Type type, Errors errors) {
			// Si erreur de validation, retour erreur 400 (bad request), avec le
			// message d'erreur
			if (errors.hasErrors()) {
				return AjaxResponseError.extractErrorWhenIncompletRequest(errors);
			}

			try {
				typeService.updateType(type);
			} catch (Exception e) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_EXEC, e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.OK).body(type);
		}

		/**
		 * suppression d'un acteur
		 * 
		 * @param id : id de l'acteur
		 * @return : réponse de la requête sans contenu
		 */
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<?> deleteType(@PathVariable("id") Long id) {

			try {
				typeService.deleteType(id);
			} catch (Exception e) {
				return AjaxResponseError.getErrorMessage(AjaxResponseError.ERROR_EXEC, e.getMessage());
			}

			return ResponseEntity.noContent().build();
		}

}
