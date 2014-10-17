 #!/bin/bash
 
if [ -d ../deploy ]
then
    echo "Delatando link existente para: ${1}"
    rm -f ../deploy
fi

echo "Criando link para: ${1}"
ln -s ${1} ../deploy

