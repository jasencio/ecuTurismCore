package ec.turismvisitplanner.core.services;

import ec.turismvisitplanner.core.exceptions.CustomException;
import ec.turismvisitplanner.core.models.File;
import ec.turismvisitplanner.core.models.Organization;
import ec.turismvisitplanner.core.payload.request.OrganizationRequest;
import ec.turismvisitplanner.core.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private S3Service s3Service;

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization createOrganization(OrganizationRequest organizationRequest) {
        if (organizationRequest == null) {
            throw new IllegalArgumentException("Organization request cannot be null");
        }

        if (organizationRequest.getName() == null || organizationRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Organization name cannot be null or empty");
        }
        
        if (organizationRequest.getImageBase64() == null || organizationRequest.getImageBase64().trim().isEmpty()) {
            throw new IllegalArgumentException("Image cannot be null or empty");
        }

        File fileSaved;
        try {
            // Decode base64 string to byte array
            String base64Image = organizationRequest.getImageBase64();
            if (base64Image.contains(",")) {
                base64Image = base64Image.split(",")[1];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            
            // Create a ByteArrayInputStream from the decoded bytes
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            
            // Upload to S3
            fileSaved = s3Service.uploadFileFromStream(inputStream, "image.jpg");
        } catch (IOException e) {
            throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
        }

        Date now = new Date();
        Organization organization = Organization.builder()
                .name(organizationRequest.getName().trim())
                .description(organizationRequest.getDescription())
                .phone(organizationRequest.getPhone())
                .address(organizationRequest.getAddress())
                .isActive(organizationRequest.getIsActive())
                .createdAt(now)
                .updatedAt(now)
                .image(fileSaved)
                .build();
        return organizationRepository.save(organization);
    }

    public Organization updateOrganization(String id, OrganizationRequest organizationRequest) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Organization ID cannot be null or empty");
        }

        Optional<Organization> existingOrganization = organizationRepository.findById(id);
        
        if (existingOrganization.isEmpty()) {
            return null;
        }

        Organization organization = existingOrganization.get();
        
        // Update fields only if they are not null in the request
        if (organizationRequest != null) {
            if (organizationRequest.getName() != null && !organizationRequest.getName().trim().isEmpty()) {
                organization.setName(organizationRequest.getName().trim());
            }
            if (organizationRequest.getDescription() != null) {
                organization.setDescription(organizationRequest.getDescription());
            }
            if (organizationRequest.getPhone() != null) {
                organization.setPhone(organizationRequest.getPhone());
            }
            if (organizationRequest.getAddress() != null) {
                organization.setAddress(organizationRequest.getAddress());
            }
            organization.setIsActive(organizationRequest.getIsActive());
        }

        // Update image if provided
        if (organizationRequest != null && organizationRequest.getImageBase64() != null && !organizationRequest.getImageBase64().trim().isEmpty()) {
            try {
                // Decode base64 string to byte array
                String base64Image = organizationRequest.getImageBase64();
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                
                // Create a ByteArrayInputStream from the decoded bytes
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                
                // Upload to S3
                File fileSaved = s3Service.uploadFileFromStream(inputStream, "image.jpg");
                organization.setImage(fileSaved);
            } catch (IOException e) {
                throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
            }
        }

        // Always update the updatedAt timestamp
        organization.setUpdatedAt(new Date());

        return organizationRepository.save(organization);
    }

    public Organization getOrganizationById(String id) {
        Optional<Organization> organization = organizationRepository.findById(id);
        if(organization.isEmpty()) {
            throw new IllegalArgumentException("Organization not found");
        }
        return organization.get();
    }
}
