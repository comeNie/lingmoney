/*
 * jQuery placeholder, fix for IE6,7,8,9
 */
var JPlaceHolder = {
    //���
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //��ʼ��
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //�޸�
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<div class="ssss"></div>').css({position:'relative', zoom:'1',float:'left', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:pos.top, height:'34px', lineHeight:'34px', paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//ִ��
jQuery(function(){
    JPlaceHolder.init();    
});