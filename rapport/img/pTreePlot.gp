set terminal svg
set output 'pTreePlot.svg'
set xlabel "nombre de clés"
set ylabel "temps"
set style data linespoints

temps_theorique(x) = (x*x*45/1000000)
plot [0:1000] 'pTreePlot.dat' using 1:2 title 'Temps mesuré avec openjdk7',\
       'raw_data/pTreeSpeed.dat' using 1:3 title 'temps mesuré avec openjdk8
       # temps_theorique(x) title 'Temps théorique pour une complexité quadratique'
