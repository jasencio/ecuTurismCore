package ec.tourismvisitplanner.core.services;

import ec.tourismvisitplanner.core.exceptions.CustomException;
import ec.tourismvisitplanner.core.models.File;
import ec.tourismvisitplanner.core.models.Organization;
import ec.tourismvisitplanner.core.payload.request.OrganizationRequest;
import ec.tourismvisitplanner.core.repository.OrganizationRepository;
import ec.tourismvisitplanner.core.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrganizationService {

    private OrganizationRepository organizationRepository;
    private FileUtils fileUtils;

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
            // Upload to S3
            fileSaved = fileUtils.saveImageBase64(organizationRequest.getImageBase64());
        } catch (IOException e) {
            throw new CustomException("Archivo no pudo ser guardado", HttpStatus.BAD_REQUEST.value());
        }

        Date now = new Date();
        Organization organization = Organization.builder()
                .name(organizationRequest.getName().trim())
                .description(organizationRequest.getDescription())
                .phone(organizationRequest.getPhone())
                .address(organizationRequest.getAddress())
                .timeOpenWeek(organizationRequest.getTimeOpenWeek())
                .timeCloseWeek(organizationRequest.getTimeCloseWeek())
                .timeOpenSaturday(organizationRequest.getTimeOpenSaturday())
                .timeCloseSaturday(organizationRequest.getTimeCloseSaturday())
                .timeOpenSunday(organizationRequest.getTimeOpenSunday())
                .timeCloseSunday(organizationRequest.getTimeCloseSunday())
                .daysWeekEnabled(organizationRequest.getDaysWeekEnabled())
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

            if (organizationRequest.getTimeOpenWeek() != null) {
                organization.setTimeOpenWeek(organizationRequest.getTimeOpenWeek());
            }
            if (organizationRequest.getTimeCloseWeek() != null) {
                organization.setTimeCloseWeek(organizationRequest.getTimeCloseWeek());
            }
            if (organizationRequest.getTimeOpenSaturday() != null) {
                organization.setTimeOpenSaturday(organizationRequest.getTimeOpenSaturday());
            }
            if (organizationRequest.getTimeCloseSaturday() != null) {
                organization.setTimeCloseSaturday(organizationRequest.getTimeCloseSaturday());
            }
            if (organizationRequest.getTimeOpenSunday() != null) {
                organization.setTimeOpenSunday(organizationRequest.getTimeOpenSunday());
            }
            if (organizationRequest.getTimeCloseSunday() != null) {
                organization.setTimeCloseSunday(organizationRequest.getTimeCloseSunday());
            }
            if (organizationRequest.getDaysWeekEnabled() != null) {
                organization.setDaysWeekEnabled(organizationRequest.getDaysWeekEnabled());
            }
            
            organization.setIsActive(organizationRequest.getIsActive());
        }

        // Update image if provided
        if (organizationRequest != null && organizationRequest.getImageBase64() != null && !organizationRequest.getImageBase64().trim().isEmpty()) {
            try {
                File fileSaved;
                // Upload to S3
                fileSaved = fileUtils.saveImageBase64(organizationRequest.getImageBase64());
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
        if (organization.isEmpty()) {
            throw new IllegalArgumentException("Organization not found");
        }
        return organization.get();
    }
}
