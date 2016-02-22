// Compiled by ClojureScript 1.7.170 {}
goog.provide('cljs.repl');
goog.require('cljs.core');
cljs.repl.print_doc = (function cljs$repl$print_doc(m){
cljs.core.println.call(null,"-------------------------");

cljs.core.println.call(null,[cljs.core.str((function (){var temp__4425__auto__ = new cljs.core.Keyword(null,"ns","ns",441598760).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(temp__4425__auto__)){
var ns = temp__4425__auto__;
return [cljs.core.str(ns),cljs.core.str("/")].join('');
} else {
return null;
}
})()),cljs.core.str(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Protocol");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m))){
var seq__25760_25774 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"forms","forms",2045992350).cljs$core$IFn$_invoke$arity$1(m));
var chunk__25761_25775 = null;
var count__25762_25776 = (0);
var i__25763_25777 = (0);
while(true){
if((i__25763_25777 < count__25762_25776)){
var f_25778 = cljs.core._nth.call(null,chunk__25761_25775,i__25763_25777);
cljs.core.println.call(null,"  ",f_25778);

var G__25779 = seq__25760_25774;
var G__25780 = chunk__25761_25775;
var G__25781 = count__25762_25776;
var G__25782 = (i__25763_25777 + (1));
seq__25760_25774 = G__25779;
chunk__25761_25775 = G__25780;
count__25762_25776 = G__25781;
i__25763_25777 = G__25782;
continue;
} else {
var temp__4425__auto___25783 = cljs.core.seq.call(null,seq__25760_25774);
if(temp__4425__auto___25783){
var seq__25760_25784__$1 = temp__4425__auto___25783;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__25760_25784__$1)){
var c__25407__auto___25785 = cljs.core.chunk_first.call(null,seq__25760_25784__$1);
var G__25786 = cljs.core.chunk_rest.call(null,seq__25760_25784__$1);
var G__25787 = c__25407__auto___25785;
var G__25788 = cljs.core.count.call(null,c__25407__auto___25785);
var G__25789 = (0);
seq__25760_25774 = G__25786;
chunk__25761_25775 = G__25787;
count__25762_25776 = G__25788;
i__25763_25777 = G__25789;
continue;
} else {
var f_25790 = cljs.core.first.call(null,seq__25760_25784__$1);
cljs.core.println.call(null,"  ",f_25790);

var G__25791 = cljs.core.next.call(null,seq__25760_25784__$1);
var G__25792 = null;
var G__25793 = (0);
var G__25794 = (0);
seq__25760_25774 = G__25791;
chunk__25761_25775 = G__25792;
count__25762_25776 = G__25793;
i__25763_25777 = G__25794;
continue;
}
} else {
}
}
break;
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m))){
var arglists_25795 = new cljs.core.Keyword(null,"arglists","arglists",1661989754).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_((function (){var or__24604__auto__ = new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m);
if(cljs.core.truth_(or__24604__auto__)){
return or__24604__auto__;
} else {
return new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m);
}
})())){
cljs.core.prn.call(null,arglists_25795);
} else {
cljs.core.prn.call(null,((cljs.core._EQ_.call(null,new cljs.core.Symbol(null,"quote","quote",1377916282,null),cljs.core.first.call(null,arglists_25795)))?cljs.core.second.call(null,arglists_25795):arglists_25795));
}
} else {
}
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"special-form","special-form",-1326536374).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Special Form");

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.contains_QMARK_.call(null,m,new cljs.core.Keyword(null,"url","url",276297046))){
if(cljs.core.truth_(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))){
return cljs.core.println.call(null,[cljs.core.str("\n  Please see http://clojure.org/"),cljs.core.str(new cljs.core.Keyword(null,"url","url",276297046).cljs$core$IFn$_invoke$arity$1(m))].join(''));
} else {
return null;
}
} else {
return cljs.core.println.call(null,[cljs.core.str("\n  Please see http://clojure.org/special_forms#"),cljs.core.str(new cljs.core.Keyword(null,"name","name",1843675177).cljs$core$IFn$_invoke$arity$1(m))].join(''));
}
} else {
if(cljs.core.truth_(new cljs.core.Keyword(null,"macro","macro",-867863404).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"Macro");
} else {
}

if(cljs.core.truth_(new cljs.core.Keyword(null,"repl-special-function","repl-special-function",1262603725).cljs$core$IFn$_invoke$arity$1(m))){
cljs.core.println.call(null,"REPL Special Function");
} else {
}

cljs.core.println.call(null," ",new cljs.core.Keyword(null,"doc","doc",1913296891).cljs$core$IFn$_invoke$arity$1(m));

if(cljs.core.truth_(new cljs.core.Keyword(null,"protocol","protocol",652470118).cljs$core$IFn$_invoke$arity$1(m))){
var seq__25764 = cljs.core.seq.call(null,new cljs.core.Keyword(null,"methods","methods",453930866).cljs$core$IFn$_invoke$arity$1(m));
var chunk__25765 = null;
var count__25766 = (0);
var i__25767 = (0);
while(true){
if((i__25767 < count__25766)){
var vec__25768 = cljs.core._nth.call(null,chunk__25765,i__25767);
var name = cljs.core.nth.call(null,vec__25768,(0),null);
var map__25769 = cljs.core.nth.call(null,vec__25768,(1),null);
var map__25769__$1 = ((((!((map__25769 == null)))?((((map__25769.cljs$lang$protocol_mask$partition0$ & (64))) || (map__25769.cljs$core$ISeq$))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__25769):map__25769);
var doc = cljs.core.get.call(null,map__25769__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists = cljs.core.get.call(null,map__25769__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name);

cljs.core.println.call(null," ",arglists);

if(cljs.core.truth_(doc)){
cljs.core.println.call(null," ",doc);
} else {
}

var G__25796 = seq__25764;
var G__25797 = chunk__25765;
var G__25798 = count__25766;
var G__25799 = (i__25767 + (1));
seq__25764 = G__25796;
chunk__25765 = G__25797;
count__25766 = G__25798;
i__25767 = G__25799;
continue;
} else {
var temp__4425__auto__ = cljs.core.seq.call(null,seq__25764);
if(temp__4425__auto__){
var seq__25764__$1 = temp__4425__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__25764__$1)){
var c__25407__auto__ = cljs.core.chunk_first.call(null,seq__25764__$1);
var G__25800 = cljs.core.chunk_rest.call(null,seq__25764__$1);
var G__25801 = c__25407__auto__;
var G__25802 = cljs.core.count.call(null,c__25407__auto__);
var G__25803 = (0);
seq__25764 = G__25800;
chunk__25765 = G__25801;
count__25766 = G__25802;
i__25767 = G__25803;
continue;
} else {
var vec__25771 = cljs.core.first.call(null,seq__25764__$1);
var name = cljs.core.nth.call(null,vec__25771,(0),null);
var map__25772 = cljs.core.nth.call(null,vec__25771,(1),null);
var map__25772__$1 = ((((!((map__25772 == null)))?((((map__25772.cljs$lang$protocol_mask$partition0$ & (64))) || (map__25772.cljs$core$ISeq$))?true:false):false))?cljs.core.apply.call(null,cljs.core.hash_map,map__25772):map__25772);
var doc = cljs.core.get.call(null,map__25772__$1,new cljs.core.Keyword(null,"doc","doc",1913296891));
var arglists = cljs.core.get.call(null,map__25772__$1,new cljs.core.Keyword(null,"arglists","arglists",1661989754));
cljs.core.println.call(null);

cljs.core.println.call(null," ",name);

cljs.core.println.call(null," ",arglists);

if(cljs.core.truth_(doc)){
cljs.core.println.call(null," ",doc);
} else {
}

var G__25804 = cljs.core.next.call(null,seq__25764__$1);
var G__25805 = null;
var G__25806 = (0);
var G__25807 = (0);
seq__25764 = G__25804;
chunk__25765 = G__25805;
count__25766 = G__25806;
i__25767 = G__25807;
continue;
}
} else {
return null;
}
}
break;
}
} else {
return null;
}
}
});

//# sourceMappingURL=repl.js.map