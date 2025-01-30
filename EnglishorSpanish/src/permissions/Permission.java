package permissions;

public interface Permission {
    void grantPermission(String cardId, String target);
    void revokePermission(String cardId, String target);
}
