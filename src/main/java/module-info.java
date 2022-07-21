/*
 * author: ClÃ©ment Levallois
 */

module net.clementlevallois.umigon.explain {
    
    requires net.clementlevallois.umigon.model;
    requires net.clementlevallois.umigon.core;
    requires jakarta.json;
    
    exports net.clementlevallois.umigon.explain.controller;
    exports net.clementlevallois.umigon.explain.parameters;
}
