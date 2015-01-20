#! /usr/bin/env python3.4
import pymysql
import string
import logging
import logging.handlers
import re
from sys import exit

FORMAT = '[%(levelname)s] - %(asctime)s - %(message)s'

logging.basicConfig(format=FORMAT)
logger = logging.getLogger('data')
logger.setLevel('DEBUG')


def postBodiesToDocuments() :
    # connect to server
    try:
        conn = pymysql.connect(host='192.168.174.1', port=3306, user='root', passwd='', db='stackoverflow', charset='utf8', autocommit=True)
        curr = conn.cursor()
        logger.info('Database Connection: successfully connected.')
    except:
        logger.error('Database Connection: unable to connect.')
        logger.error('Database Connection: cannot proceed, terminating.')
        exit(1)

    statement = "SELECT id, body FROM bootstrap;"
    curr.execute(statement)
    rows = curr.fetchall()
    for row in rows :
        body = re.sub(r'(<code>.*</code>)', ' ', row[1])
        body = re.sub(r'https?://[^\s<>"]+|www\.[^\s<>"]+', ' ', body)
        body = re.sub('<[^<]+?>', '', body)
        body = re.sub('&[#a-zA-Z ]+?;', '', body)
        body = re.sub('([\w]*[-!@#$%^&*+_)(*&/<>:"{}\\|=\]\[{}/.\']+?[\w]*)', '', body)
        filename = str(row[0]) + ".txt"
        f = open('./data/' + filename, 'w')
        logger.debug('Writing post %s to a file.', row[0])
        f.write(body)

    logger.info('Database Connection: closing connection.')
    curr.close()
    conn.close()

postBodiesToDocuments()

# Afterwards you can run mallet as follows:
# /opt/mallet/bin/mallet import-dir --input ./data --output topic-input.mallet --keep-sequence --remove-stopwords
# /opt/mallet/bin/mallet train-topics --input topic-input.mallet --num-topics 40 --output-state topic-state.gz --output-topic-keys topic_keys --num-top-words 20

