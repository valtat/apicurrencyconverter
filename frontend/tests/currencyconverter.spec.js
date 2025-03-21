import { test, expect } from "@playwright/test";

test("should be able to choose a currency and convert it", async ({ page }) => {
  await page.goto("https://kind-island-02bf6e003.6.azurestaticapps.net/");
  await page.getByLabel("Amount:").click();
  await page.getByLabel("Amount:").fill("110");
  await page.getByLabel("To:").selectOption("USD");
  await page.getByRole("button", { name: "Convert" }).click();
  await expect(page.locator("#root")).toContainText("EUR is equal to", "USD");
});
