package cz.vvoleman.phr.featureMedicalRecord.ui.view.detailGallery

import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder
import cz.vvoleman.phr.featureMedicalRecord.databinding.FragmentDetailGalleryBinding
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.addEdit.AssetPresentationModel
import cz.vvoleman.phr.featureMedicalRecord.presentation.model.detailGallery.DetailGalleryViewState
import cz.vvoleman.phr.featureMedicalRecord.ui.adapter.GalleryAdapter
import cz.vvoleman.phr.featureMedicalRecord.ui.model.ImageItemUiModel

class DetailGalleryBinder :
    BaseViewStateBinder<DetailGalleryViewState, FragmentDetailGalleryBinding, DetailGalleryBinder.Notification>() {

    override fun firstBind(viewBinding: FragmentDetailGalleryBinding, viewState: DetailGalleryViewState) {
        super.firstBind(viewBinding, viewState)

        val images = viewState.medicalRecord
            .assets
            .map { ImageItemUiModel(AssetPresentationModel(it.id, it.createdAt, it.url)) }
        val galleryAdapter = GalleryAdapter(images)

        viewBinding.viewPager.apply {
            adapter = galleryAdapter
            val currentItem = viewState.selectedAssetId?.let { assetId ->
                images.indexOfFirst { it.asset.id == assetId }
            } ?: 0
            setCurrentItem(currentItem)
//            PagerSnapHelper().attachToRecyclerView(this)
        }

        viewBinding.viewPager.registerOnPageChangeCallback(object : androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewBinding.textViewCurrentPage.text = "${position + 1}/${images.size}"
            }
        })
    }

    sealed class Notification
}
