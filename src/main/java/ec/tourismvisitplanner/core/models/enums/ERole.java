package ec.tourismvisitplanner.core.models.enums;

public enum ERole {
  ADMIN_SYSTEM("ADMIN_SYSTEM"),
  ADMIN_COMPANY("ADMIN_COMPANY"),
  TOURIST_GUIDE("TOURIST_GUIDE"),
  TOURIST("TOURIST");

  private final String value;

  private ERole(final String value) {
    this.value = value;
  }

  public String getValue() { return value; }
}
