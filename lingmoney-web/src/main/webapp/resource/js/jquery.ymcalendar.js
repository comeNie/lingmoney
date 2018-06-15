/**
 * 年月选择插件
 * chenjun 20130217
 */
(function($) {
	$.fn.ymcalendar = function(options, param) {
		if (typeof options == 'string'){
			return $.fn.ymcalendar.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var target = $(this);
			var state = $.data(this, 'ymcalendar');
			if (state){
				$.extend(state.options, options);
			}
			else {
				$.data(this, 'ymcalendar', {
					options: $.extend({}, $.fn.ymcalendar.defaults, {
						disabled: (target.attr('disabled') ? true : false),
						onchange: target[0].onchange
					}, options)
				});
				target[0].onchange = null;
				target.removeAttr('disabled');
			}
			create(target);
		});
	};

	$.fn.ymcalendar.defaults = {
		disabled: false
	};

	$.fn.ymcalendar.methods = {
		enable: function(jq) {
			return jq.each(function() {
				setDisabled(this, false);
			});
		},
		disable: function(jq){
			return jq.each(function(){
				setDisabled(this, true);
			});
		}
	};

	function create(target) {
		var state = $.data(target[0], 'ymcalendar');
		//注册keydown方法
		target.keydown(function() {
			return false;
		});
		//注册click方法
		target.click(function() {
			var year;
			var flag = true;
			var ymcalendar = $("<div class='ymcalendar-Div' style='left:"+target.offset().left+"px;top:"+(target.offset().top+target.height()+4)+"px'></div>");
			var tab = $("<table></table>");

			var tr = $("<tr></tr>");
			var td = $("<td class='ymcalendar-text'>&lt;&lt;</td>");
			td.mousedown(function() {
				flag = false;
				year.attr("year", parseInt(year.attr("year")) - 1);
				year.html(year.attr("year") + "年");
			});
			td.mouseup(function() {
				flag = true;
				target[0].focus();
			});
			tr.append(td);

			var y = new Date().getFullYear();
			y = target.val() != "" ? target.val().substring(0,target.val().length-2) : y;
			year = $("<td class='ymcalendar-text' year='"+y+"' colspan='2'>"+y+"年</td>");
			tr.append(year);

			td = $("<td class='ymcalendar-text'>&gt;&gt;</td>");
			td.mousedown(function() {
				flag = false;
				year.attr("year", parseInt(year.attr("year")) + 1);
				year.html(year.attr("year") + "年");
			});
			td.mouseup(function() {
				flag = true;
				target[0].focus();
			});
			tr.append(td);
			tab.append(tr);

			var m = target.val() != "" ? Number(target.val().substring(target.val().length-2)) : 0;
			for (var i = 0; i < 12; i++) {
				if (i%4 == 0) {
					tr = $("<tr></tr>");
					tab.append(tr);
				}
				var cls;
				if (m == i+1) cls = "ymcalendar-selected";
				else cls = "ymcalendar-text";
				td = $("<td class='"+cls+"'>"+(i+1)+"月</td>");
				td.mousedown(function() {
					var mon = $(this).html().replace("月","");
					mon = mon.length==1?"0"+mon:mon;
					//alert(year.attr("year").substring(0,4))
					var newval = year.attr("year").substring(0,4)+mon;
					if (target.val() != newval) {
						target.val(year.attr("year").substring(0,4)+"-"+mon);
						if (state.options.onchange)
							state.options.onchange();
					}
				});
				tr.append(td);
			}
			ymcalendar.append(tab);

			tab.children().find("td").mouseover(function() {
				var cls = $(this).attr("class");
				$(this).removeAttr("class");
				if (cls.indexOf("selected")>0)
					$(this).addClass("ymcalendar-selected-over");
				else
					$(this).addClass("ymcalendar-text-over");
			});
			tab.children().find("td").mouseout(function() {
				var cls = $(this).attr("class");
				$(this).removeAttr("class");
				if (cls.indexOf("selected")>0)
					$(this).addClass("ymcalendar-selected");
				else
					$(this).addClass("ymcalendar-text");
			});
			year.unbind("mouseover");
			year.unbind("mouseout");

			$(document.body).append(ymcalendar);
			target.attr("box", ymcalendar);
			target.blur(function(){
				if (flag) {
					ymcalendar.remove();
					target.attr("box", null);
				}
			});
		});

		if (state.options.disabled && !target.attr('disabled')) {
			setDisabled(target[0], true);
		}
		else if (!state.options.disabled && target.attr('disabled')) {
			setDisabled(target[0], false);
		}
	}

	function setDisabled(target, disabled) {
		var state = $.data(target, 'ymcalendar');
		if (disabled) {
			state.options.disabled = true;
			if (target.onclick){
				state.onclick = target.onclick;
				target.onclick = null;
			}
			$(target).attr('disabled',true);
		}
		else {
			state.options.disabled = false;
			if (state.onclick) {
				target.onclick = state.onclick;
			}
			$(target).attr('disabled',false);
		}
		return false;
	}
})(jQuery);

$(function() { $(".ymcalendar").ymcalendar(); });
