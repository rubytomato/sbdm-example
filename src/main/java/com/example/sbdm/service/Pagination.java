package com.example.sbdm.service;


public interface Pagination {

  default int currentPage(final int pageNo) {
    int calcPage = pageNo - 1;
    if (calcPage < 0) {
      calcPage = 0;
    }
    return calcPage;
  }

  default int maxPage(final int max, final int pageSize) {
    int calcPage = max / pageSize;
    if (max % pageSize != 0) {
      calcPage++;
    };
    return calcPage;
  }

  default PageBean calcPage(final int max, final int pageNo, final int pageSize) {
    PageBean page = new PageBean();
    page.setTotalCount(max);
    page.setCurrentPage(currentPage(pageNo) + 1);
    page.setMaxPage(maxPage(max, pageSize));
    return page;
  }

}
