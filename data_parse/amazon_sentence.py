
from pandas import DataFrame as df
import csv
import filepath as fp
from nltk.tokenize import sent_tokenize


def save_sentences():
    review_df = df.from_csv("/home/trideep/PycharmProjects/mr2vec/data/amazon/amazon_category_reviews.csv")
    #print(review_df.head())

    summary_df, complete_df = review_df['summary'], review_df['reviewText']

    csv_writer = csv.writer(open("/home/trideep/PycharmProjects/mr2vec/data/amazon/amazon_sentences.csv","w"))
    csv_writer.writerow(["index","sentence"])

    count = 0
    for row in summary_df:
        sentences = sent_tokenize(row)
        for sentence in sentences:
            count += 1
            csv_writer.writerow([count,sentence])

    for row in complete_df:
        try:
            sentences = sent_tokenize(row)
        except Exception as e:
            continue
        for sentence in sentences:
            try:
                count += 1
                csv_writer.writerow([count,sentence])
            except Exception as e:
                pass

    print(str(count) + " number of lines written")




save_sentences()