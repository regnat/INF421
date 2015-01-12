LATEX=latexmk
all: rapport_INF421.pdf

rapport_INF421.pdf: rapport_INF421.tex images
	$(LATEX) $<

images:
	@(cd img && $(MAKE))
