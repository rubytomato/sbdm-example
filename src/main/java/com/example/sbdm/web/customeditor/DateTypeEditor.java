package com.example.sbdm.web.customeditor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeEditor extends PropertyEditorSupport {

  /* (non-Javadoc)
   * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
   */
  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    if (text == null || text.length() == 0){
      return;
    }
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    Date date = null;
    try {
      date = format.parse(text);
    } catch (ParseException e) {
      // nop
    }
    setValue(date);
  }

}
