import {expect, Page} from '@playwright/test';

export class NavMenu {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }



    async clickAction(action: string) {
        await this.page.locator('.buttons button').filter({hasText: action}).click();
    }

    async getRouteAction() {
        const response = this.page.waitForResponse((res) =>
            res.url().includes('graphhopper.com'));

        await this.page.locator('.buttons button').filter({hasText: 'Добиј Рута'}).click();
        await response;
    }
    async readReviewsAction() {
        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/review/all/'));

        await this.page.locator('.buttons button').filter({hasText: 'Читај Мислење'}).click();
        await response;
    }

    async validateRouteIsDisplayed() {
        const routingLocator = this.page.locator('div.leaflet-routing-container');
        await expect(routingLocator).toBeVisible();
    }

    async validateReviewsAreDisplayed() {
        await expect(this.page.locator('.review-section')).toBeVisible()

        await expect(this.page.locator('h3:hasText("Просечна оценка: ")')).toBeVisible()
        await expect(this.page.locator('#Review').first()).toBeVisible()
    }
}