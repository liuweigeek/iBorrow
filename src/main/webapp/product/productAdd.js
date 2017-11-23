/**
 * Created by Scott on 17/6/27.
 */

$(function(){
    $('#search-product-type-keyword').bind('keypress',function(event){
        if(event.keyCode == "13") {
            event.stopPropagation();
            searchTypeByKeyword();
            return false;
        }
    });

    $('#search-homeclassify-keyword').bind('keypress',function(event){
        if(event.keyCode == "13") {
            event.stopPropagation();
            searchHomeClassifyByKeyword();
            return false;
        }
    });

    $('#search-homezone-keyword').bind('keypress',function(event){
        if(event.keyCode == "13") {
            event.stopPropagation();
            searchHomeZoneByKeyword();
            return false;
        }
    });
});

/* ----------------- ProductType ----------------- */

function searchTypeByKeyword() {
    $(".search-product-type-menu span").remove();
    $(".search-product-type-menu input").remove();
    var keyword = $("#search-product-type-keyword").val();
    $.post("ProductType_listByKeyword.action", {keyword: keyword},
        function(result){
            var result = eval('('+result+')');
            if (!result.error) {
                var types = result.types;
                for(var i = 0; i < types.length; i++){
                    var item = "<span class='dropdown-item' onclick='addToProductType(" + types[i].id + ", \"" + types[i].title + "\");'>" + types[i].title + "</span>";
                    /* var item = "<span class='dropdown-item' onclick='javascript:addToProductType(" + types[i] + ");'>" + types[i].title + "</span>"; */
                    $(".search-product-type-menu").append(item);
                }
                $('#search-product-type-keyword').dropdown('toggle');
            }
        }
    );
}

function addToProductType(id, title) {
    var item = "<span class='badge badge-pill badge-primary badge-type'>" + title + "</span>";
    var hiddenItem = "<input type='hidden' name='productTypeId' value='" + id + "'>";
    $("#selected-product-types-zone").append(item);
    $("#selected-product-types-zone").append(hiddenItem);
}

/* ----------------- HomeClassify ----------------- */

function searchHomeClassifyByKeyword() {
    $(".search-homeclassify-menu span").remove();
    $(".search-homeclassify-menu input").remove();
    var keyword = $("#search-homeclassify-keyword").val();
    $.post("HomeClassify_listByKeyword.action", {keyword: keyword},
        function(result){
            var result = eval('('+result+')');
            if (!result.error) {
                var homeClassifies = result.homeClassifies;
                for(var i = 0; i < homeClassifies.length; i++){
                    var item = "<span class='dropdown-item' onclick='addToHomeClassify(" + homeClassifies[i].id + ", \"" + homeClassifies[i].name + "\");'>" + homeClassifies[i].name + "</span>";
                    $(".search-homeclassify-menu").append(item);
                }
                $('.search-homeclassify-menu').dropdown('toggle');
            }
        }
    );
}

function addToHomeClassify(id, name) {
    if ($("#homeclassify-chip-" + id).length > 0) {
        alert(name + '已添加');
    } else {
        var item = getChipItem('homeclassify-chip', id, name);
        $("#selected-homeclassify-zone").append(item);
    }
}


/* ----------------- HomeZone ----------------- */

function searchHomeZoneByKeyword() {
    $(".search-homezone-menu span").remove();
    $(".search-homezone-menu input").remove();
    var keyword = $("#search-homezone-keyword").val();
    $.post("HomeZone_listByKeyword.action", {keyword: keyword},
        function(result){
            var result = eval('('+result+')');
            if (!result.error) {
                var homezones = result.homezones;
                for(var i = 0; i < homezones.length; i++){
                    var item = "<span class='dropdown-item' onclick='addToHomeZone(" + homezones[i].id + ", \"" + homezones[i].name + "\");'>" + homezones[i].name + "</span>";
                    $(".search-homezone-menu").append(item);
                }
                $('.search-homezone-menu').dropdown('toggle');
            }
        }
    );
}

function addToHomeZone(id, name) {
    if ($("#homezone-chip-" + id).length > 0) {
        alert(name + '已添加');
    } else {
        var item = getChipItem('homezone-chip', id, name);
        $("#selected-homezone-zone").append(item);
    }
}

/* ----------------- Util ----------------- */

function getChipItem(classStr, id, name) {
    var item = "<span id='" + classStr + "-" + id + "' class='" + classStr + "'><input type='hidden' value='" + id + "'>"
        + name
        + "<span class='close-icon-span' onclick='javascript:removeChip(\"" + classStr + "-" + id + "\");'>"
        + "&times;</span></span>";
    return item;
}

function removeChip(itemId) {
    $("#"+ itemId).remove();
}

function checkForm() {
    return (setHomeClassifyIds() && setHomeZoneIds());
}

function setHomeClassifyIds() {
    var homeClassifySpans = $(".homeclassify-chip").children("input");
    if (homeClassifySpans.length > 0) {
        for (var i = 0; i < homeClassifySpans.length; i++) {
            homeClassifySpans.eq(i).attr("name", "homeClassifies[" + i + "].id");
        }
    }
    return true;
}

function setHomeZoneIds() {
    var homeZoneSpans = $(".homezone-chip").children("input");
    if (homeZoneSpans.length > 0) {
        for (var i = 0; i < homeZoneSpans.length; i++) {
            homeZoneSpans.eq(i).attr("name", "homeZones[" + i + "].id");
        }
    }
    return true;
}