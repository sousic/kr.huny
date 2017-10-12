var Utils = {};

Utils = {
    DateFormate:function(value)
    {
        if(value == null)
        {
            return "";
        }
        return (new Date(value).toString("yyyy-MM-dd HH:mm:ss"));
    }
};

/* array */
Array.prototype.contains = function(element) {
    for(var i =0;i < this.length;i++) {
        if(this[i] == element) {
            return true;
        }
    }
    return false;
};

Array.prototype.delete = function(element) {
    var index = this.indexOf(element);
    return this.splice(index,1);
};