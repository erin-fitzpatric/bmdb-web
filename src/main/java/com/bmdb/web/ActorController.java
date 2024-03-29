package com.bmdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Actor;
import com.bmdb.db.ActorRepository;

@CrossOrigin
@RestController
@RequestMapping("/actors")
public class ActorController {
	@Autowired
	private ActorRepository actorRepo;

	// list - return all actors
	@GetMapping("/")
	public JsonResponse listActors() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(actorRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	// get - return 1 actor for the given id
		@GetMapping("/{id}")
		public JsonResponse getActor(@PathVariable int id) {
			JsonResponse jr = null;
			try {
				jr = JsonResponse.getInstance(actorRepo.findById(id));
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		// add - adds a new Actor
		@PostMapping("/")
		public JsonResponse addActor(@RequestBody Actor a) {
			// add a new actor
			JsonResponse jr = null;
			try {
				jr = JsonResponse.getInstance(actorRepo.save(a));
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		// update - update a new Actor
		@PutMapping("/")
		public JsonResponse updateActor(@RequestBody Actor a) {
			// update a actor
			JsonResponse jr = null;
			try {
				if (actorRepo.existsById(a.getId())) {
					jr = JsonResponse.getInstance(actorRepo.save(a));
				} else {
					// record doesn't exist
					jr = JsonResponse.getInstance("Error updating Actor. id: " + a.getId() + " doesn't exist!");
				}
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		// delete - delete a Actor
		@DeleteMapping("/{id}")
		public JsonResponse deleteActor(@PathVariable int id) {
			// delete a actor
			JsonResponse jr = null;
			try {
				if (actorRepo.existsById(id)) {
					actorRepo.deleteById(id);;
					jr = JsonResponse.getInstance("Delete succesful!");
				} else {
					// record doesn't exist
					jr = JsonResponse.getInstance("Error deleting Actor. id: " + id + " doesn't exist!");
				}
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
}
