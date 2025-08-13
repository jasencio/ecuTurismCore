# Appointment Search by Organization with @DBRef References

This document provides comprehensive examples of how to search for appointments by organization when using `@DBRef` references in MongoDB with Spring Data.

## Data Model Structure

```java
// Appointment has @DBRef to Route
@Document(collection = "appointments")
public class Appointment {
    @DBRef
    private Route route;
    // ... other fields
}

// Route has @DBRef to Organization
@Document(collection = "routes")
public class Route {
    @DBRef
    private Organization organization;
    // ... other fields
}

// Organization is a regular document
public class Organization {
    @Id
    private String id;
    // ... other fields
}
```

## Search Approaches

### 1. **Aggregation Pipeline (Recommended - Most Efficient)**

This is the most efficient approach for nested `@DBRef` relationships.

#### Repository Method:
```java
@Aggregation(pipeline = {
    "{ $lookup: { from: 'routes', localField: 'route', foreignField: '_id', as: 'routeData' } }",
    "{ $unwind: '$routeData' }",
    "{ $lookup: { from: 'organizations', localField: 'routeData.organization', foreignField: '_id', as: 'organizationData' } }",
    "{ $unwind: '$organizationData' }",
    "{ $match: { 'organizationData._id': ?0 } }",
    "{ $project: { routeData: 0, organizationData: 0 } }"
})
List<Appointment> findByOrganizationIdUsingAggregation(String organizationId);
```

#### Usage:
```java
// In service
List<Appointment> appointments = appointmentRepository.findByOrganizationIdUsingAggregation("org123");

// With status filter
@Aggregation(pipeline = {
    "{ $lookup: { from: 'routes', localField: 'route', foreignField: '_id', as: 'routeData' } }",
    "{ $unwind: '$routeData' }",
    "{ $lookup: { from: 'organizations', localField: 'routeData.organization', foreignField: '_id', as: 'organizationData' } }",
    "{ $unwind: '$organizationData' }",
    "{ $match: { 'organizationData._id': ?0, 'status': ?1 } }",
    "{ $project: { routeData: 0, organizationData: 0 } }"
})
List<Appointment> findByOrganizationIdAndStatusUsingAggregation(String organizationId, AppointmentStatus status);
```

**Advantages:**
- Most efficient for complex queries
- Single database operation
- Can handle complex filtering and aggregation
- Best performance for large datasets

**Disadvantages:**
- More complex to write and understand
- Less type-safe

### 2. **Spring Data MongoDB Query Methods**

Using Spring Data's automatic query method generation.

#### Repository Method:
```java
List<Appointment> findByRoute_Organization_Id(String organizationId);
List<Appointment> findByRoute_Organization_IdAndStatus(String organizationId, AppointmentStatus status);
```

#### Usage:
```java
// In service
List<Appointment> appointments = appointmentRepository.findByRoute_Organization_Id("org123");
List<Appointment> pendingAppointments = appointmentRepository.findByRoute_Organization_IdAndStatus("org123", AppointmentStatus.PENDING);
```

**Advantages:**
- Simple and readable
- Type-safe
- Automatic query generation

**Disadvantages:**
- Less efficient for nested `@DBRef` relationships
- Limited to simple queries
- May cause N+1 query problems

### 3. **@Query Annotation with MongoDB Query**

Using custom MongoDB queries with the `@Query` annotation.

#### Repository Method:
```java
@Query("{ 'route.organization.$id': ?0 }")
List<Appointment> findByOrganizationId(String organizationId);

@Query("{ 'route.organization.$id': ?0, 'status': ?1 }")
List<Appointment> findByOrganizationIdAndStatus(String organizationId, AppointmentStatus status);
```

#### Usage:
```java
// In service
List<Appointment> appointments = appointmentRepository.findByOrganizationId("org123");
List<Appointment> pendingAppointments = appointmentRepository.findByOrganizationIdAndStatus("org123", AppointmentStatus.PENDING);
```

**Advantages:**
- More control over the query
- Can use MongoDB-specific features
- Good performance for simple queries

**Disadvantages:**
- Less type-safe
- Requires knowledge of MongoDB query syntax

### 4. **MongoTemplate for Complex Queries**

Using `MongoTemplate` for dynamic and complex queries.

#### Service Method:
```java
public List<Appointment> findAppointmentsByOrganizationId_MongoTemplate(String organizationId) {
    Criteria criteria = Criteria.where("route.organization.$id").is(organizationId);
    Query query = new Query(criteria);
    return mongoTemplate.find(query, Appointment.class);
}

public List<Appointment> findAppointmentsByOrganizationIdAndStatus_MongoTemplate(String organizationId, AppointmentStatus status) {
    Criteria criteria = Criteria.where("route.organization.$id").is(organizationId)
                              .and("status").is(status);
    Query query = new Query(criteria);
    return mongoTemplate.find(query, Appointment.class);
}

public List<Appointment> findAppointmentsByOrganizationIdWithMultipleCriteria_MongoTemplate(
        String organizationId, 
        AppointmentStatus status, 
        Date startDate, 
        Date endDate,
        Integer minGroupSize) {
    
    Criteria criteria = Criteria.where("route.organization.$id").is(organizationId);
    
    if (status != null) {
        criteria.and("status").is(status);
    }
    
    if (startDate != null && endDate != null) {
        criteria.and("eventDate").gte(startDate).lte(endDate);
    }
    
    if (minGroupSize != null) {
        criteria.and("groupSize").gte(minGroupSize);
    }
    
    Query query = new Query(criteria);
    return mongoTemplate.find(query, Appointment.class);
}
```

#### Usage:
```java
// In service
List<Appointment> appointments = appointmentSearchService.findAppointmentsByOrganizationId_MongoTemplate("org123");

// With multiple criteria
List<Appointment> filteredAppointments = appointmentSearchService
    .findAppointmentsByOrganizationIdWithMultipleCriteria_MongoTemplate(
        "org123", 
        AppointmentStatus.PENDING, 
        startDate, 
        endDate, 
        5
    );
```

**Advantages:**
- Maximum flexibility
- Dynamic query building
- Can handle complex business logic
- Good for conditional queries

**Disadvantages:**
- More verbose
- Requires more code
- Less type-safe

### 5. **Organization Object Reference**

Fetching the organization first, then using it in the query.

#### Repository Method:
```java
List<Appointment> findByRouteOrganizationAndStatus(Organization organization, AppointmentStatus status);
```

#### Service Method:
```java
public List<Appointment> findAppointmentsByOrganization_Object(String organizationId) {
    Optional<Organization> organization = organizationRepository.findById(organizationId);
    if (organization.isPresent()) {
        return appointmentRepository.findByRouteOrganizationAndStatus(organization.get(), null);
    }
    return List.of();
}
```

#### Usage:
```java
// In service
List<Appointment> appointments = appointmentSearchService.findAppointmentsByOrganization_Object("org123");
```

**Advantages:**
- Type-safe with Organization objects
- Can validate organization exists
- Good for business logic that needs the organization object

**Disadvantages:**
- Requires additional database query to fetch organization
- Less efficient
- More complex

## Performance Comparison

| Method | Performance | Complexity | Flexibility | Type Safety |
|--------|-------------|------------|-------------|-------------|
| Aggregation Pipeline | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐ |
| MongoTemplate | ⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| @Query Annotation | ⭐⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| Query Methods | ⭐⭐ | ⭐ | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| Object Reference | ⭐ | ⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐⭐ |

## API Endpoints Examples

### Basic Search
```bash
# Aggregation (recommended)
GET /api/appointment-search/organization/{organizationId}/aggregation

# Query methods
GET /api/appointment-search/organization/{organizationId}/query-method

# Query annotation
GET /api/appointment-search/organization/{organizationId}/query-annotation

# MongoTemplate
GET /api/appointment-search/organization/{organizationId}/mongotemplate

# Object reference
GET /api/appointment-search/organization/{organizationId}/object
```

### Search with Status Filter
```bash
# All methods support status filtering
GET /api/appointment-search/organization/{organizationId}/aggregation/status/PENDING
GET /api/appointment-search/organization/{organizationId}/mongotemplate/status/CONFIRMED
```

### Advanced Search with Multiple Criteria
```bash
# MongoTemplate with date range
GET /api/appointment-search/organization/{organizationId}/mongotemplate/date-range?startDate=2024-01-01&endDate=2024-12-31

# MongoTemplate with multiple criteria
GET /api/appointment-search/organization/{organizationId}/mongotemplate/advanced?status=PENDING&minGroupSize=5&startDate=2024-01-01&endDate=2024-12-31
```

### Statistics and Comparison
```bash
# Get appointment statistics
GET /api/appointment-search/organization/{organizationId}/statistics

# Compare all methods
GET /api/appointment-search/organization/{organizationId}/compare
```

## Best Practices

### 1. **Choose the Right Method**

- **Use Aggregation Pipeline** for:
  - Complex queries with multiple joins
  - Large datasets
  - Performance-critical operations
  - When you need to aggregate data

- **Use MongoTemplate** for:
  - Dynamic queries with conditional logic
  - Complex business rules
  - When you need maximum flexibility

- **Use Query Methods** for:
  - Simple queries
  - When type safety is important
  - Small datasets

- **Use @Query Annotation** for:
  - Simple custom queries
  - When you need MongoDB-specific features

### 2. **Indexing**

Ensure you have proper indexes for efficient queries:

```javascript
// Index on route field for faster lookups
db.appointments.createIndex({ "route": 1 })

// Compound index for common queries
db.appointments.createIndex({ "route": 1, "status": 1, "eventDate": 1 })
```

### 3. **Error Handling**

Always handle cases where the organization doesn't exist:

```java
public List<Appointment> findAppointmentsByOrganization(String organizationId) {
    if (organizationId == null || organizationId.trim().isEmpty()) {
        return List.of();
    }
    
    try {
        return appointmentRepository.findByOrganizationIdUsingAggregation(organizationId);
    } catch (Exception e) {
        log.error("Error finding appointments for organization: " + organizationId, e);
        return List.of();
    }
}
```

### 4. **Pagination**

For large result sets, implement pagination:

```java
@Aggregation(pipeline = {
    "{ $lookup: { from: 'routes', localField: 'route', foreignField: '_id', as: 'routeData' } }",
    "{ $unwind: '$routeData' }",
    "{ $lookup: { from: 'organizations', localField: 'routeData.organization', foreignField: '_id', as: 'organizationData' } }",
    "{ $unwind: '$organizationData' }",
    "{ $match: { 'organizationData._id': ?0 } }",
    "{ $skip: ?1 }",
    "{ $limit: ?2 }",
    "{ $project: { routeData: 0, organizationData: 0 } }"
})
List<Appointment> findByOrganizationIdWithPagination(String organizationId, int skip, int limit);
```

## Conclusion

The **Aggregation Pipeline** approach is generally the most efficient for searching appointments by organization when using `@DBRef` references. However, choose the method that best fits your specific use case, considering factors like:

- Query complexity
- Performance requirements
- Type safety needs
- Development team expertise
- Maintenance requirements

For most production applications, a combination of Aggregation Pipeline for complex queries and MongoTemplate for dynamic queries provides the best balance of performance and flexibility. 