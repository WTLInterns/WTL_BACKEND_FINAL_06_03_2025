package com.workshop.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.workshop.Entity.CabAdmin;
import com.workshop.Entity.OutSourceCarCab;
import com.workshop.Repo.VehicleRepository;

@Service
public class VehicleService {

    // Directory where the images will be stored
    private static final String IMAGE_DIR = "src/main/resources/static/images/outSourceImg/";

    @Autowired
    private VehicleRepository vehicleRepository;

    // Create or Update vehicle with multiple images
    public OutSourceCarCab saveVehicle(OutSourceCarCab vehicle,
                                       MultipartFile insurance,
                                       MultipartFile permit,
                                       MultipartFile authorization,
                                       MultipartFile carImage,
                                       MultipartFile frontImage,
                                       MultipartFile backImage,
                                       MultipartFile sideImage) throws IOException {

        // Save images and set their file names
        vehicle.setInsurance(saveImage(insurance, "insurance"));
        vehicle.setPermit(saveImage(permit, "permit"));
        vehicle.setAuthorization(saveImage(authorization, "authorization"));
        vehicle.setCarImage(saveImage(carImage, "carImage"));
        vehicle.setFrontImage(saveImage(frontImage, "frontImage"));
        vehicle.setBackImage(saveImage(backImage, "backImage"));
        vehicle.setSideImage(saveImage(sideImage, "sideImage"));

        // Debugging: print the vehicle object to verify its state before saving
        System.out.println("Vehicle object to save: " + vehicle);

        return vehicleRepository.save(vehicle); // Save the vehicle details with the image paths
    }

    // Get all vehicles
    public List<OutSourceCarCab> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Optional<OutSourceCarCab> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    // Delete vehicle by ID
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    // Helper method to save the image and return the file name
    private String saveImage(MultipartFile image, String imageType) throws IOException {
        if (image != null && !image.isEmpty()) {
            // Create directory if not exists
            File directory = new File(IMAGE_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Get the file name, adding a timestamp to avoid collisions
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path destinationPath = Path.of(directory.getAbsolutePath(), fileName);

            // Save the image to the file system
            Files.copy(image.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Log file name
            System.out.println(imageType + " saved with file name: " + fileName);

            return fileName;  // Return the saved file name
        }

        // Return a default image if no file is provided
        return "default.jpg";
    }
    
    
    public OutSourceCarCab udpateStatus(Long id, String status) {
    	OutSourceCarCab outSourceCarCab = this.vehicleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cart not found"));
    	outSourceCarCab.setStatus(status);
		    return vehicleRepository.save(outSourceCarCab);
    }
    
    
	
}
