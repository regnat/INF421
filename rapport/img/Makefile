PLOTS=$(wildcard *.gp)
EPSIMGs=$(PLOTS:.gp=.svg)
IMGS=$(EPSIMGs:.svg=.eps)

*.eps: *.svg
	convert $^ $@

*.svg: *.gp *.dat
	gnuplot $<
