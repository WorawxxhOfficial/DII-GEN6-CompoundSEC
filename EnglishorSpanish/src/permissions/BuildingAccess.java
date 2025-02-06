package permissions;

public class BuildingAccess implements Permission {
    @Override
    public void grantPermission(String cardId, String target) {
        System.out.println("Building access granted: Card " + cardId + " can access " + target);
    }

    @Override
    public void revokePermission(String cardId, String target) {
        System.out.println("Building access revoked: Card " + cardId + " cannot access " + target);
    }
}
