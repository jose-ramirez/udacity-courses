#!/usr/bin/env bash
mkcdir () {
    mkdir -p -- "$1" &&
      cd -P -- "$1"
}

mkcdir "particles"
python3 -m venv myvenv
source myvenv/bin/activate
pip install --upgrade pip
pip install --upgrade setuptools
pip install django
