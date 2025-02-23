package com.workshop.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.workshop.Entity.CabAdmin;
import com.workshop.Entity.OutSourceCarCab;
import com.workshop.Service.CabAdminService;
import com.workshop.Service.VehicleService;

@RestController
@RequestMapping("/cabAdmin")
public class CabAdminController {

    @Autowired
    private CabAdminService cabAdminService;

    // Create or Update vehicle
    @PostMapping("/save")
    public ResponseEntity<CabAdmin> saveCabAdmin(
            @RequestParam("vehicleNameAndRegNo") String vehicleNameAndRegNo,
            @RequestParam("vehicleRcNo") String vehicleRcNo,
            @RequestParam("carOtherDetails") String carOtherDetails,
            @RequestParam("status") String status,
//            @RequestParam("vehicleName") String vehicleName,

            
            
            @RequestParam("vehicleRcImg") MultipartFile vehicleRcImg,
            @RequestParam("insurance") MultipartFile insurance,
            @RequestParam("permit") MultipartFile permit,
            @RequestParam("fitnessCert") MultipartFile fitnessCert,
            @RequestParam("cabImage") MultipartFile cabImage,
            @RequestParam("frontImage") MultipartFile frontImage,
            @RequestParam("backImage") MultipartFile backImage,
            @RequestParam("sideImage") MultipartFile sideImage

            ) {

        CabAdmin cabAdmin = new CabAdmin();
        cabAdmin.setVehicleNameAndRegNo(vehicleNameAndRegNo);
        cabAdmin.setVehicleRcNo(vehicleRcNo);
        cabAdmin.setCarOtherDetails(carOtherDetails);
        cabAdmin.setStatus(status);
//        cabAdmin.setVehicleName(vehicleName);

        try {
            // Prepare an array of images to pass to the service
            MultipartFile[] images = new MultipartFile[] {
            		vehicleRcImg, insurance, permit, 
            		fitnessCert, frontImage, backImage, sideImage,cabImage
            };

            CabAdmin cabAdminSaved = cabAdminService.saveCabAdmin(cabAdmin, vehicleRcImg, insurance, permit, fitnessCert, cabImage, frontImage, backImage, sideImage);
            return ResponseEntity.ok(cabAdminSaved);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Get all vehicles
    @GetMapping("/all")
    public ResponseEntity<List<CabAdmin>> getAllVehicles() {
        List<CabAdmin> vehicles = cabAdminService.getCabAdmin();
        return ResponseEntity.ok(vehicles);
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<CabAdmin> getVehicleById(@PathVariable Long id) {
        return cabAdminService.getCabAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
	public ResponseEntity<CabAdmin> changeStatus( @PathVariable Long id, @RequestBody Map<String, String> requestBody) {

	   
	    String status = requestBody.get("status");

	    try {
	    	CabAdmin updatedOrder = cabAdminService.udpateStatus(id, status);
	        return ResponseEntity.ok(updatedOrder);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

    
}