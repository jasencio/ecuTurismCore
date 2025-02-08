package ec.turismvisitplanner.core.models.enums;

public enum ERole {
  ROLE_ADMIN_SYSTEM("ROLE_ADMIN_SYSTEM"),
  ROLE_ADMIN_COMPANY("ROLE_ADMIN_COMPANY"),
  ROLE_TOURIST_GUIDE("ROLE_TOURIST_GUIDE"),
  ROLE_TOURIST("ROLE_TOURIST");

  private final String value;

  private ERole(final String value) {
    this.value = value;
  }

  public String getValue() { return value; }
}
