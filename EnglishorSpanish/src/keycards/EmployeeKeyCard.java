package keycards;

import permissions.Permission;

public class EmployeeKeyCard extends KeyCard {
    private Permission permission;

    public EmployeeKeyCard(String cardId, String owner, Permission permission) {
        super(cardId, owner);
        this.permission = permission;
    }

    @Override
    public void use() {
        System.out.println("Employee " + owner + " is using KeyCard " + cardId);
        permission.grantPermission(cardId, "Employee Area");
    }
}
