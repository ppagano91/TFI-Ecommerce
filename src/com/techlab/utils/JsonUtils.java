package com.techlab.utils;

public class JsonUtils {

  public static int parseInt(Object obj, int defaultValue) {
    if (obj == null) return defaultValue;

    try {
      if (obj instanceof Number) {
        return ((Number) obj).intValue();
      } else {
        return Integer.parseInt(obj.toString());
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static double parseDouble(Object obj, double defaultValue) {
    if (obj == null) return defaultValue;

    try {
      if (obj instanceof Number) {
        return ((Number) obj).doubleValue();
      } else {
        return Double.parseDouble(obj.toString());
      }
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }
}

