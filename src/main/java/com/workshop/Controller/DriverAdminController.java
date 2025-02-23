package com.workshop.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.workshop.Entity.DriveAdmin;
import com.workshop.Entity.OutSourceCarCab;
import com.workshop.Service.DriverAdminService;

@RestController
@RequestMapping("/driverAdmin")
public class DriverAdminController {

	
	@Autowired
	private DriverAdminService driverAdminService;
	
	 @PostMapping("/save")
	    public ResponseEntity<DriveAdmin> saveDriveAdmin(
	            @RequestParam("DriverName") String DriverName,
	            @RequestParam("ContactNo") String ContactNo,
	            @RequestParam("AltMobNum") String AltMobNum,
	            @RequestParam("Adress") String Adress,
	            @RequestParam("emailId") String emailId,
	            @RequestParam("status") String status,
	            @RequestParam("otherDetails") String otherDetails,
	            @RequestParam("aadhaNo") String aadhaNo,
	            @RequestParam("drLicenseNo") String drLicenseNo,
	            @RequestParam("pvcNo2") String pvcNo2,

	            


	            
	            @RequestParam("DriverImgSelfie") MultipartFile DriverImgSelfie,
	            @RequestParam("Aadhar") MultipartFile Aadhar,
	            @RequestParam("DrLicenceNum") MultipartFile DrLicenceNum,
	            @RequestParam("PvcNo") MultipartFile PvcNo
	            
	            
	            
	            ) {

	        DriveAdmin driverAdmin = new DriveAdmin();
	        driverAdmin.setDriverName(DriverName);
	        driverAdmin.setContactNo(ContactNo);
	        driverAdmin.setAltMobNum(AltMobNum);
	        driverAdmin.setAdress(Adress);
	        driverAdmin.setEmailId(emailId);
	        driverAdmin.setOtherDetails(otherDetails);
	        driverAdmin.setStatus(status);

	        try {
	            // Prepare an array of images to pass to the service
	            MultipartFile[] images = new MultipartFile[] {
	            		DriverImgSelfie, Aadhar, DrLicenceNum, 
	            		PvcNo
	            };

	            DriveAdmin savedDriverAdmin = driverAdminService.saveDriverAdmin(driverAdmin, DriverImgSelfie, Aadhar, DrLicenceNum, PvcNo);
	            return ResponseEntity.ok(savedDriverAdmin);
	        } catch (IOException e) {
	            return ResponseEntity.status(500).body(null);
	        }
	    }

	    // Get all vehicles
	    @GetMapping("/all")
	    public ResponseEntity<List<DriveAdmin>> getAllDriverAdmin() {
	        List<DriveAdmin> driverAdmin = driverAdminService.getDriverAdmin();
	        return ResponseEntity.ok(driverAdmin);
	    }

	    // Get vehicle by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<DriveAdmin> getVehicleById(@PathVariable int id) {
	        return driverAdminService.getDriverAdminById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

//	    // Delete vehicle by ID
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
//	        vehicleService.deleteVehicle(id);
//	        return ResponseEntity.noContent().build();
//	    }
}
