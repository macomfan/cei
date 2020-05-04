#!/usr/bin/env python3
from setuptools import setup

setup(
    name="cei-python",
    version="0.0.1",
    packages=['impl'],
    install_requires=['requests', 'urllib3', 'websocket-client']
)