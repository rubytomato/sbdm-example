package com.example.sbdm.service;


public interface IService<T> extends Pagination {

  /**
   * コレクションのドキュメントの件数
   */
  public long count();

  /**
   * ドキュメントIDで検索
   */
  public T findById(String id);

  /**
   * 条件なしでドキュメントを検索
   */
  public Iterable<T> findAll(int page, int size, String sortColumn);

  /**
   * ドキュメントのプライマリーキーで検索
   */
  public T findByPk(Object...keys);

  /**
   * 名称のlike検索
   */
  public Iterable<T> findByNameLike(String name, String sortColumn);

  /**
   * 検索ワードに一致するドキュメントの件数
   */
  public long searchCount(String keyword);

  /**
   * 検索ワードに一致するドキュメントを検索
   */
  public Iterable<T> search(String keyword, int page, int size, String sortColumn);

  public T save(T model);

  public Iterable<T> save(Iterable<T> model);

  public void delete(String id);

  public void delete(Iterable<T> model);

}
