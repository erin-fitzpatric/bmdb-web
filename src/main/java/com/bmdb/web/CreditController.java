package com.bmdb.web;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.Credit;
import com.bmdb.db.CreditRepository;

@CrossOrigin
@RestController
@RequestMapping("/credits")
public class CreditController {

	@Autowired
	private CreditRepository creditRepo;

	// list - return all credits
	@GetMapping("/")
	public JsonResponse listCredits() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	// get - return 1 credit for the given id
	@GetMapping("/{id}")
	public JsonResponse getCredit(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.findById(id));
		} 
		catch (ConstraintViolationException cve) {
			jr = JsonResponse.getInstance(cve);
		}
		
		catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	// add - adds a new Credit
	@PostMapping("/")
	public JsonResponse addCredit(@RequestBody Credit c) {
		// add a new credit
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(creditRepo.save(c));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
			e.printStackTrace();
		}
		return jr;
	}

	// update - update a new Credit
	@PutMapping("/")
	public JsonResponse updateCredit(@RequestBody Credit c) {
		// update a credit
		JsonResponse jr = null;
		try {
			if (creditRepo.existsById(c.getId())) {
				jr = JsonResponse.getInstance(creditRepo.save(c));
			} else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error updating Credit. id: " + c.getId() + " doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	// delete - delete a Credit
	@DeleteMapping("/{id}")
	public JsonResponse deleteCredit(@PathVariable int id) {
		// delete a credit
		JsonResponse jr = null;
		try {
			if (creditRepo.existsById(id)) {
				creditRepo.deleteById(id);
				;
				jr = JsonResponse.getInstance("Delete succesful!");
			} else {
				// record doesn't exist
				jr = JsonResponse.getInstance("Error deleting Credit. id: " + id + " doesn't exist!");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
}
