package jp.mamori_i.app.screen.trace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.mamori_i.app.R
import jp.mamori_i.app.extension.setUpToolBar
import jp.mamori_i.app.extension.showErrorDialog
import jp.mamori_i.app.screen.trace.TraceDataUploadViewModel.UploadState.*
import kotlinx.android.synthetic.main.activity_trace_data_upload.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class TraceDataUploadActivity: AppCompatActivity(), TraceDataUploadNavigator {
    companion object {
        const val KEY = "jp.mamori_i.app.screen.trace.TraceDataUploadActivity"
    }

    private val viewModel: TraceDataUploadViewModel by viewModel()
    private val disposable: CompositeDisposable by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初期設定
        initialize()
        // viewの初期設定
        setupViews()
        // viewModelとのbind
        bind()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun initialize() {
        setContentView(R.layout.activity_trace_data_upload)
        viewModel.navigator = this
    }

    private fun setupViews() {
        // ツールバー
        setUpToolBar(toolBar, "")

        uploadButton.setOnClickListener {
            viewModel.onClickUpload()
        }
    }

    private fun bind() {
        viewModel.uploadState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { state ->
                // TODO ステータスに応じた出しわけ
                when(state) {
                    Ready -> {}
                    InProgress -> {}
                    Complete -> {}
                    else -> {}
                }
            }
            .addTo(disposable)

        viewModel.uploadError
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { error ->
                showErrorDialog(error)
            }
            .addTo(disposable)
    }

    override fun goToHome() {
        // TODO
        finish()
    }

}