package com.freshfood.util;

public enum UserRole {
    customer,
    admin,
    staff;
    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
    @Override
    public String toString() {
        return name().toUpperCase(); // hoặc viết kiểu khác nếu cần
    }
}
