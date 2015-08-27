package com.example.sbdm.viewhelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

  public String format(Date date) {
    if (date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);
      return sdf.format(date);
    }
    return "***";
  }

}
