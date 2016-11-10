package com.lisa.televisa.model;

/**
 * Created by hever on 11/10/16.
 */

public class Article {

    private String content;
    private String date_gmt;
    private String excerpt;
    private String featured_media;
    private String guid;
    private int id;
    private String link;
    private String modified;
    private String modified_gmt;
    private String slug;
    private String title;
    private String type;
    private String _links;

    public Article(){

    }

    public Article(String content, String date_gmt, String excerpt, String featured_media, String guid, int id, String link, String modified, String modified_gmt, String slug, String title, String type, String _links){

        this.content            = content;
        this.date_gmt           = date_gmt;
        this.excerpt            = excerpt;
        this.featured_media     = featured_media;
        this.guid               = guid;
        this.id                 = id;
        this.link               = link;
        this.modified           = modified;
        this.modified_gmt       = modified_gmt;
        this.slug               = slug;
        this.title              = title;
        this.type               = type;
        this._links             = _links;

    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getDate_gmt(){
        return date_gmt;
    }

    public void setDate_gmt(String date_gmt){
        this.date_gmt = date_gmt;
    }

    public String getExcerpt(){
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getFeatured_media() {
        return featured_media;
    }

    public void setFeatured_media(String featured_media) {
        this.featured_media = featured_media;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModified_gmt() {
        return modified_gmt;
    }

    public void setModified_gmt(String modified_gmt) {
        this.modified_gmt = modified_gmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_links() {
        return _links;
    }

    public void set_links(String _links) {
        this._links = _links;
    }
}
